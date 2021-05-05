--用户Id
local userId = KEYS[1]
--用户购买数量
local buynum = tonumber(KEYS[2])
--用户购买的SKU
local skuid = KEYS[3]
--每人购买此SKU的数量限制
local perSkuLimit = tonumber(KEYS[4])
--活动Id
local actId = KEYS[5]
--此活动中商品每人购买限制
local perActLimit = tonumber(KEYS[6])
--订单下单时间
local ordertime = KEYS[7]
--每个用户购买的某一sku数量
local user_sku_hash = 'sec_'..actId..'_u_sku_hash'
--每个用户购买的某一活动中商品的数量(已购买)
local user_act_hash = 'sec_'..actId..'_u_act_hash'
--sku的库存数
local sku_amount_hash = 'sec_'..actId..'_sku_amount_hash'
--秒杀成功的记录数
local second_log_hash = 'sec_'..actId..'_u_sku_hash'

--判断的流程:

--判断商品库存数（当前sku是否还有库存）
local skuAmountStr = redis.call('hget',sku_amount_hash,skuid) --获取目前sku的库存量
if skuAmountStr == false then  --如果没有获取到，则说明商品设置有误，直接返回异常
    redis.log(redis.LOG_NOTICE,'skuAmountStr is nil ')
    return '-3'
end

local skuAmount = tonumber(skuAmountStr)
--如果库存不大于0，则说明无库存，不能再抢购
if skuAmount <= 0 then
    return '0'
end

local userActKey = userId..'_'..actId
--判断用户已购买的同一sku数量，

if perActLimit > 0 then  --如果每个人可以抢购的数量大于0，才能进行抢购，否则逻辑错误
    local userActNumInt = 0
    --获取该活动中该用户已经抢购到的数量
    local userActNum = redis.call('hget',user_act_hash,userActKey)
    --如果没有获取到，则说明用户还未抢购到，直接抢购用户下单的数量
    if userActNum == false then
        userActNumInt = buynum
    else   --如果获取到了用户在该活动中已经抢购到的数量，则用户抢购成功后的sku总量=原有数量 + 本次下单数量
        local curUserActNumInt = tonumber(userActNum)
        userActNumInt = curUserActNumInt + buynum
    end

    --如果抢购成功后用户在活动中抢购sku的数量大于每个用户限制的数量，则返回异常
    if userActNumInt > perActLimit then
        return '-2'
    end
end

--判断用户已购买的同一秒杀活动中的商品数量
local goodsUserKey = userId..'_'..skuid
if perSkuLimit > 0 then --判断每个用户允许下单该sku的最大数量
    --获取用户已购买的sku数量
    local goodsUserNum = redis.call('hget',user_sku_hash,goodsUserKey)
    local goodsUserNumInt = 0
    --逻辑同上，如果获取异常，说明用户目前没有购买过该sku，那么秒杀成功后购买sku的数量就是本次购买数量，否则就是本次购买数量 + 原有已购sku数量
    if goodsUserNum == false then
        goodsUserNumInt = buynum
    else
        local curSkuUserNumInt = tonumber(goodsUserNum)
        goodsUserNumInt = curSkuUserNumInt + buynum
    end
    --逻辑同上，如果本次购买成功后已购sku数量大于限制值，则返回异常
    if goodsUserNumInt > perSkuLimit then
        return '-1'
    end
end

--如果库存数量大于秒杀数量，则将sku库存减一；将用户购买该sku的数量加一；将用户在该活动中抢购sku数量加一；将用户秒杀成功数加一；最终返回订单号
if skuAmount >= buynum then
    local decrNum = 0-buynum
    -- sku库存减一
    redis.call('hincrby',sku_amount_hash,skuid,decrNum)
    -- 用户购买该sku的数量加一
    if perSkuLimit > 0 then
        redis.call('hincrby',user_sku_hash,goodsUserKey,buynum)
    end
    -- 用户在该活动中抢购sku数量加一
    if perActLimit > 0 then
        redis.call('hincrby',user_act_hash,userActKey,buynum)
    end
    local orderKey = userId..'_'..'_'..buynum..'_'..ordertime
    local orderStr = '1'
    -- 用户秒杀成功数加一
    redis.call('hset',second_log_hash,orderKey,orderStr)
    return orderKey
else
    return '0'
end