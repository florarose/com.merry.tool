# HashMap
## 容量相关
### 属性
  1. capacity: 默认大小为16，当前数组容量，始终保持2^n，可以扩容，扩容后的数组的大小为当前的2倍。
  2. loadFactor：负载因子，默认为 0.75。
  3. threshold：扩容的阈值，等于 capacity * loadFactor。
### 为什么默认初始化桶数组大小为16 ？ 为何是2的整数倍？
  ```
        static int indexFor(int h, int length) { 
            return h & (length-1);
        }
  ```
  - 一、为何是2的倍数
      - HashMap 中通过h&(length-1) 的方法代替取模法。取模的消耗较大
        对于我们来说，适合10进制运算,也就是哟弄个取模运算。而计算机适合二进制运算,在实现位运算的时候是很高效的,故用与运算。
      1. length 为2的整数次幂的话，h&(length-1)即相当于对length取模，这样即保证了散列的均匀，也提升了效率;   
      2. length 为2的整数次幂的话，length-1为奇数，奇数的最后一位是1，这样的结果是h&(length-1)的最后一位可能   
         是0，也可能是1，都取决于h的值，与后的结果就有了奇偶两种可能，这样也可以保证散列的均匀性;
      3. 如果length为奇数，length-1 必定为偶数，最后一位是0;h&(length-1)的最后一位也必定是0，即是偶数。如次  
         以来任何hash值都只会被散列到数组的偶数下标位置上，这就浪费了近一半的空间。
      - 因此，length取2的整数次幂，是为了使用不同hash值发生碰撞的概率较小，这样就可以使元素在哈希表中均匀地散列。   
  - 二、为何是16？   
      - 太大，浪费空间，影响效率；太小，容易发生扩容影响性能。在使用时，预估元素的个数能够有效的提高hashmap的性能
      - 因为16对应的2进制数为1111，如此数组的下标就等同于Hashcode后四位的值，只要输入的hash值本身分布均匀，那么数组的
        下标必然均匀，hashmap默认长度为16的目的就是为了解决hash碰撞的机率。
      - 如果是14，二进制位1110，最后一位为0，导致散列函数计算后为0001,0101等永远不会出现，则位置就不能存放元素，增加了   
        散列碰撞。
      - 2的幂次方拥有更低的碰撞机率和更高的查询速率。
## java_1.7
### 一、结构
  1. 整体上说hashMap中是一个Entry数组,数组中的每一个元素是一个单向链表。      
     - 结构如下：
         ```
             Node{
                  int hash;
                  K key;
                  V value;
                  Node<K,V> next;
             }
         ```
        包含四个属性，key、value、hash值和用于单向链表的next。
     - 链表数组
         ```
             Node<K,V>[] table
         ```
  2. put 操作
     - put() 方法
        ```
        public V put(K key, V value) {
           一、 // 如果table是空表，需要先初始化数组的大小(构造函数初始化容量)，再插入第一个元素
            if (table == EMPTY_TABLE) {
                inflateTable(threshold);
            }
           二、判断key值
              1 // 如果 key 为 null(则hash值也为0)，则将该键值对存放到数组table中的第一个位置，即table[0]
            if (key == null)
                return putForNullKey(value);
              2 // key 值不是null
            // 2.1. 求 key 的 hash 值
               int hash = hash(key);
            // 2.2 根据hash值，找到存储位置对应的数组下标;计算数组的具体位置
                int i = indexFor(hash, table.length);
           三、 遍历一下对应下标处的链表，看是否有重复的 key 已经存在，
                //如果有，直接覆盖，put 方法返回旧值就结束了
                for (Entry<K,V> e = table[i]; e != null; e = e.next) {
                    Object k;
                    if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                        V oldValue = e.value;
                        e.value = value;
                        e.recordAccess(this);
                        return oldValue;
                    }
                }
                 modCount++;
            四、 不存在重复的 key，将此 entry 添加到链表中
            addEntry(hash, key, value, i);
            return null;
         }
        ```
        一. inflateTable(threshold);
        ```
             private void inflateTable(int toSize) {  
                   // 1. 将传入的容量大小转化为：>传入容量大小的最小的2的次幂
                   // 即如果传入的是容量大小是19，那么转化后，初始化容量大小为32（即2的5次幂）
                   ->>分析1
                   int capacity = roundUpToPowerOf2(toSize);   
                
                   // 2. 重新计算阈值 threshold = 容量 * 加载因子  
                   threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);  
                
                   // 3. 使用计算后的初始容量（已经是2的次幂） 初始化数组table（作为数组长度）
                   // 即 哈希表的容量大小 = 数组大小（长度）
                   table = new Entry[capacity]; //用该容量初始化table  
                    
                   initHashSeedAsNeeded(capacity);  
             }  
        ```
          - roundUpToPowerOf2(int toSize);
            ```
                * 分析1：roundUpToPowerOf2(toSize)
                * 作用：将传入的容量大小转化为：>传入容量大小的最小的2的幂
                * 特别注意：容量大小必须为2的幂，该原因在下面的讲解会详细分析
                private static int roundUpToPowerOf2(int number) {  
                     //若容量超过了最大值，初始化容量设置为最大值 ；否则，设置为：>传入容量大小的最小的2的次幂
                      return number >= MAXIMUM_CAPACITY  ? 
                      MAXIMUM_CAPACITY  : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1; 
                }
            ```
        二. putForNullKey(value)
        ```
             private V putForNullKey(V value) {  
                // 遍历以table[0]为首的链表，寻找是否存在key==null 对应的键值对
                // 1. 若有：则用新value 替换 旧value；同时返回旧的value值
                for (Entry<K,V> e = table[0]; e != null; e = e.next) {  
                  if (e.key == null) {   
                    V oldValue = e.value;  
                    e.value = value;  
                    e.recordAccess(this);  
                    return oldValue;  
                }  
             }  
        ```
        ```
            /**
             * Returns index for hash code h.
             */
            static int indexFor(int h, int length) {
                // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
                return h & (length-1);
            } 
       ```
        四. addEntry(hash, key, value, i);作用：添加键值对（Entry ）到 HashMap中
        ```
            void addEntry(int hash, K key, V value, int bucketIndex) {  
                   hash 插入数组table中的索引位置   bucketIndex 数组下标
                   先判断容量是否足够，若不足够，则进行扩容（2倍）、重新计算Hash值、重新计算存储数组下标；
                  if ((size >= threshold) && (null != table[bucketIndex])) {  
                    resize(2 * table.length); // a. 扩容2倍  --> 分析1
                    hash = (null != key) ? hash(key) : 0;  // b. 重新计算该Key对应的hash值
                    bucketIndex = indexFor(hash, table.length);  // c. 重新计算该Key对应的hash值的存储数组下标位置
                  }
                  如果足够，创建Entry
                  createEntry(hash, key, value, bucketIndex);
            }  
        ```
        ```
          void createEntry(int hash, K key, V value, int bucketIndex) {
                  Entry<K,V> e = table[bucketIndex];
                  table[bucketIndex] = new Entry<>(hash, key, value, e);
                  size++;
          }
        ```
         ```
           void resize(int newCapacity) {
                   Entry[] oldTable = table;
                   int oldCapacity = oldTable.length;
                   if (oldCapacity == MAXIMUM_CAPACITY) {
                       threshold = Integer.MAX_VALUE;
                       return;
                   }
                    // 定义新的数组
                   Entry[] newTable = new Entry[newCapacity];
                    // 将原来数组中的值迁移到新的更大的数组中
                   transfer(newTable, initHashSeedAsNeeded(newCapacity));
                   table = newTable;
                   threshold = (int)Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
           }
        ```   
       - 重点讲解，此方法执行的是扩容操作。首先介绍三个参数 
         static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16  当前数组容量，始终保持 2^n
         static final float DEFAULT_LOAD_FACTOR = 0.75f;   // 负载因子
         int threshold;   //扩容的阈值
         threshold = DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR ;  默认是等于12   
        扩容的条件:     
           一般情况下：   
               当前数据存储的数量，大小大于等于阙值;   
               当前加入的数据是否发生了hash冲突。   
           意外：   
                当然也有可能存储更多值（超多16个值，最多可以存26个值）都还没有扩容。   
           比如：前11个值全部hash碰撞，存到数组的同一个位置（这时元素个数小于阈值12，不会扩容），   
                后面所有存入的15个值全部分散到数组剩下的15个位置（这时元素个数大于等于阈值，但是每次存入的元素并没有发生hash碰撞，所以不会扩容），   
                前面11+15=26，所以在存入第27个值的时候才同时满足上面两个条件，这时候才会发生扩容现象    
         
    

