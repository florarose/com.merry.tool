//package com.study.thread.SchedulingConfig;
//
//import com.study.thread.mapper.TScheduledTaskMapper;
//import com.study.thread.model.TScheduledTask;
//import com.study.thread.service.impl.TScheduledTaskServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author 坎布里奇
// * @version 1.0
// * @date 2019/11/4 18:12
// */
//
//@Component
//public class OrderUnPayTimerService  extends DynamicScheduledTask{
//
//    @Autowired
//    ScheduleConfi timerConfig;
//
//    @Autowired
//    TScheduledTaskServiceImpl tScheduledTaskServiceImpl;
//    @Autowired
//    TScheduledTaskMapper tScheduledTaskMapper;
//    /**
//     * 更新未支付订单的状态为已取消
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void doBiz() {
//        System.out.println("OrderUnPayTimerService >>>>>>>>>> 定时器开始执行 " + LocalDateTime.now().toLocalTime());
//        try {
//            /**
//             * 设置定时器执行频率
//             */
//            setCron("0/10 * * * * ?");
//
//            /**
//             * 1.获取3天未支付的订单ID
//             */
//
//
//           TScheduledTask tScheduledTask = tScheduledTaskMapper.selectOneTask();
//           if(null != tScheduledTask){
//               System.out.println("执行定时任务");
//               tScheduledTaskServiceImpl.updateTaskSchedule(tScheduledTask.getId());
//           }
//
//            /**
//             * 4.操作数据库做保存和更新操作
//             */
////            if (null != orderList && orderList.size() > 0) {
////                shopOrderExMapper.updateAllStatus(orderList);
////            }
////
////            if (null != orderStatusHistoryList && orderStatusHistoryList.size() > 0) {
////                shopOrderStatusHistoryMapper.insertBatch(orderStatusHistoryList);
////            }
//
//            System.out.println("OrderUnPayTimerService >>>>>>>>>> 定时器结束执行 " + LocalDateTime.now().toLocalTime());
//        } catch (Exception e) {
//            System.out.println("定时器更新未支付订单异常！{}" + e.getMessage());
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    /**
//     * 订单状态历史记录
//     * @param orderId
//     * @return
//     */
////    private ShopOrderStatusHistory saveOrderStatusHistory(Integer orderId) {
////        if (null == orderId) {
////            return null;
////        }
////        ShopOrderStatusHistory shopOrderStatusHistory = new ShopOrderStatusHistory();
////        shopOrderStatusHistory.setOrderId(orderId);
////        shopOrderStatusHistory.setOrderStatusId(Constants.OrderStatus.STATUS_CANCEL);
////        shopOrderStatusHistory.setRemark("timer auto cancel");
////        shopOrderStatusHistory.setCreateBy(1);
////        shopOrderStatusHistory.setCreateDate(new Date());
////        shopOrderStatusHistory.setUpdateBy(1);
////        shopOrderStatusHistory.setUpdateDate(new Date());
////
////        return shopOrderStatusHistory;
////    }
//
//    /**
//     * 订单状态更新
//     * @param orderId
//     * @return
//     */
////    private ShopOrder updateShopOrder(Integer orderId) {
////        if (null == orderId) {
////            return null;
////        }
////        ShopOrder shopOrder = new ShopOrder();
////        shopOrder.setId(orderId);
////        shopOrder.setOrderStatus(Constants.OrderStatus.STATUS_CANCEL);
////        shopOrder.setUpdateDate(new Date());
////
////        return shopOrder;
////    }
//
//}
