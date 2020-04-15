package vedio;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.ApiError;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.api.object.PutFileApi;
import cn.ucloud.ufile.api.object.policy.PolicyParam;
import cn.ucloud.ufile.api.object.policy.PutPolicy;
import cn.ucloud.ufile.api.object.policy.PutPolicyForCallback;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.bean.UfileErrorBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.http.OnProgressListener;
import cn.ucloud.ufile.http.UfileCallback;
import cn.ucloud.ufile.util.JLog;
import cn.ucloud.ufile.util.StorageType;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author ldt merry
 * @date 2020/3/6
 */
public class VedioDemo {


private static final String TAG = "PutObjectSample";
private static ObjectConfig config = new ObjectConfig("cn-sh2", "ufileos.com");
    private static ConcurrentHashMap<Integer, LinkedBlockingQueue<String>> wrapperProgressMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<Integer, Date> lastProgressTimeMap = new ConcurrentHashMap();


    public static void putFileAsync(File file, String mimeType, String nameAs, String toBucket,int id) throws UfileClientException {
        /**
         * 上传回调策略
         * 必须填写回调接口url(目前仅支持http，不支持https)，可选填回调参数，回调参数请自行决定是否需要urlencode
         * 若配置上传回调，则上传接口的回调将会透传回调接口的response，包括httpCode
         */

      final   PutPolicy putPolicy = new PutPolicyForCallback.Builder("")
                .addCallbackBody(new PolicyParam("key", "value"))
                .build();
        UfileClient.object(Constants.OBJECT_AUTHORIZER, config)
                .putObject(file, mimeType)
                .nameAs(nameAs)
                .toBucket(toBucket)
                /**
                 * 配置文件存储类型，分别是标准、低频、冷存，对应有效值：STANDARD | IA | ARCHIVE
                 */
                .withStorageType(StorageType.STANDARD)
                /**
                 * 为云端对象配置自定义数据，每次调用将会替换之前数据。
                 * 所有的自定义数据总大小不能超过 8KB。
                 */
//                    .withMetaDatas()
                /**
                 * 为云端对象添加自定义数据，可直接调用，无须先调用withMetaDatas
                 * key不能为空或者""
                 *
                 */
//                    .addMetaData(new Parameter<>("key","value"))
                /**
                 * 配置上传回调策略
                 */
//                .withPutPolicy(putPolicy)
                /**
                 * 是否上传校验MD5
                 */
//                .withVerifyMd5(false)
                /**
                 * 指定progress callback的间隔
                 */
//                .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                /**
                 * 配置读写流Buffer的大小, Default = 256 KB, MIN = 4 KB, MAX = 4 MB
                 */
//                .setBufferSize(4 << 20)
                .executeAsync(new UfileCallback<PutObjectResultBean>() {
                    @Override
                    public void onProgress(long bytesWritten, long contentLength) {


                        LinkedBlockingQueue<String> progressQueue = new LinkedBlockingQueue();
                        JLog.D(TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength));
                        progressQueue.offer((int) (bytesWritten * 1.f / contentLength * 100)+"'");
                        wrapperProgressMap.put(id, progressQueue);
                        try {

                                    System.out.println("111'"+wrapperProgressMap.get(id).take());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(PutObjectResultBean response) {
                        JLog.D(TAG,putPolicy.getPolicy());
                        JLog.D(TAG,response.getCallbackRet());
                        JLog.D(TAG,response.geteTag());

                        JSONObject jsonObject = (JSONObject) JSONObject.parse(response.toString());
                        if(null != jsonObject){
                            System.out.println("aa:"+jsonObject.get("RetCode"));
                        }

                        JLog.D(TAG, String.format("[res] = %s", (response == null ? "null" : response.toString())));
                    }

                    @Override
                    public void onError(Request request, ApiError error, UfileErrorBean response) {
                        JLog.D(TAG, String.format("[error] = %s\n[info] = %s",
                                (error == null ? "null" : error.toString()),
                                (response == null ? "null" : response.toString())));
                    }
                });
    }

    public static void main(String[] args)  {
        InputStream is = new ByteArrayInputStream(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});
        // 如果上传File，则文件的MimeType可以使用MimeTypeUtil.getMimeType(File)来获取，MimeTypeUtil可能支持的type类型不全，用户可以按需自行填写
        QuickEmailToWikiExtractor quickEmailToWikiExtractor = new QuickEmailToWikiExtractor(10,1,1);
        try {
            for (int i = 0; i < 15; i++) {

                File file = new File("C:\\Imag.mov");
                String keyName = "" + file.getName() + "_" + i;
                String bucketName = "";
//                putFileAsync(file, "video/quicktime", keyName, bucketName,i);
                final PutFileApi response = UfileClient.object(Constants.OBJECT_AUTHORIZER, config)
                        .putObject(file,"video/quicktime")
                        .nameAs(keyName)
                        .toBucket(bucketName)
                        /**
                         * 配置文件存储类型，分别是标准、低频、冷存，对应有效值：STANDARD | IA | ARCHIVE
                         */
                        .withStorageType(StorageType.IA)
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(long bytesWritten, long contentLength) {
                                JLog.D(TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength));
                            }
                        });
//                ExecutorService executorService = Executors.newSingleThreadExecutor();
//                Set<Callable<PutObjectResultBean>> callables = new HashSet<Callable<PutObjectResultBean>>();
//                callables.add(()->{
//                  return   response
//                          .setOnProgressListener(new OnProgressListener() {
//                      @Override
//                      public void onProgress(long bytesWritten, long contentLength) {
//                          LinkedBlockingQueue<String> progressQueue = new LinkedBlockingQueue();
//                          progressQueue.offer((int) (bytesWritten * 1.f / contentLength * 100)+"'");
//                          wrapperProgressMap.put(1, progressQueue);
//                          JLog.D(TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength));
//                          try {
//                              System.out.println("list:"+wrapperProgressMap.get(1).take());
//                          }catch (InterruptedException e){
//                              e.printStackTrace();
//                          }
//
//                      }
//                  }).execute();
//                });
//                List<Future<PutObjectResultBean>> futures = executorService.invokeAll(callables);
//                for(Future<PutObjectResultBean> future : futures){
//                    System.out.println("list:"+wrapperProgressMap.get(1).take());
//                    System.out.println("future.get = " + future.get());
//                }
                quickEmailToWikiExtractor.produceEleAsync(response);


//                putFile(file, "video/quicktime", keyName, bucketName);
            }
            for (int i = 0; i < 3; i++) {
                quickEmailToWikiExtractor.consumeEleAsync();
            }

//            try {
//                Thread.sleep(10000);
//                for (int j = 0; j < 3; j++) {
//                    System.out.println("list:" + wrapperProgressMap.get(j).peek());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }catch (Exception e){
            e.printStackTrace();
        }


//        createBuckety();
    }
    /**
     * 重写ThreadPoolExecutor里的ThreadFactory接口,在创建线程的时候，做一些事情，比如赋予名称等。设置id等。
     * 以及 beforeExecute（）和afterExecute（）方法， 可以做一些调式，打印一些日志等，在测试的时候。
     *
     *UG org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor - Written [com.imag.icommon.utils.ResponseModel@4d8bf902] as "application/json" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@7464bd91]
     *Handler dispatch failed; nested exception is java.lang.StackOverflowError
     * 多生产者与多消费者模式
     *      消费者消费的数据，有可能需要继续处理，于是消费者处理完数据之后，它又要作为生产者把数据放在新的队列里，交给其他消费者继续处理。
     *
     *生产者：
     *    抽取邮件
     * 消费者：
     *    消费邮件
     * 文件上传：
     *    线程池， 串行执行的线程池
     *    生产者
     *        上传文件，然后把上传的结果放入队列。（结果信息包括： 上传的文件名称、上传结果）
     *     消费者
     *         从队列中获取信息，即文件的名称，然后去服务器获取文件的地址。
     *
     *    生产者把上传任务，放入队列中，消费者去获取，然后消费。消费者数量=CPU*2个线程数。
     *
     *    生产者和消费者模式，生产者定义一个线程池，消费者定义一个线程池。
     *    因为上传的时间比较长，所以生产者数量取CPU*2个线程数，消费者取cpu数量，自定义的线程池,
     *     因为通过 JUC自带的Executors来进行线程池的创建，Fixed、Single的线程池中的 blockingQueue 阻塞队列都是无限长度的，可能会堆积大量请求
     *     同样，Cache和Schedule类型的线程池的 Max线程数，是Integer.Max，这样会造成大量线程的创建，这两种情况都会带来不必要的对象消耗，最终极有可能变成OOM。
     *     创建线程的地方使用自定义的线程工厂ThreadFactory来进行创建，指定当时Thread的创建环境和名字，便于后续查找及发现问题。
     *
     *   线程池的工作原理：
     *       1、如果当前运行的线程少于核心线程，则创建新线程来执行任务；（需要注意的是，此操作需要获取全局锁）
     *       2、如果运行的线程等于或对于核心线程，则将任务加入工作队列中；
     *      3、如果工作队列已满，则创建新的线程（非核心线程）来处理任务；（需要获取全局锁）
     *      4、如果创建线程超出最大线程数，则任务被拒绝，调用饱和策略；（RejectedExecutionHandler.rejectedExecution()）
     *          总结： 如此设计的原因？
     *      为了在执行execute()方法时，尽可能地避免获取全局锁；因为获取全局锁是一个严重可伸缩瓶颈。
     *      期望： 每次任务提交的时候，都是当前运行的线程数大于等于核心线程数，此时不用获取全局锁，即加入工作队列。
     *  全局锁：
     *    使用的是ReentrantLock，在addWorker方法中，默认创建的是非公平锁。
     *    关闭：
     *        考虑到应用场景的需求，如何拒绝新来的请求任务？如何处理等待队列中的任务？如何处理正在执行的任务？
     *       有三种方法可以选择：
     *1、shutdown()
     * 1. 将线程池的状态设置成ShutDown;
     * 2. 中断所有没有正在执行任务的线程。在终止前允许执行以前提交的任务；
     * 2、shutdownNow()
     * 1. 将线程池的状态设置成Stop;
     * 2. 阻止等待任务的启动并试图停止当前正在执行的任务,并返回等待执行任务的列表；不允许执行以前提交的任务。
     * 1、2 两个方法的原理：
     * 遍历线程池中的工作线程，然后逐个调用线程的interrupt方法来中断线程，所以无法响应中断的任务可能永远无法终止。
     * 调用1、2方法后，isShutdown 方法返回true。当所有任务都已关闭，才表示线程池关闭成功，调用isTerminaed方法返回true。
     * 3、awaitTermination()
     * 1. 接收timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，会监测ExecutorService是否已经
     * 关闭，若关闭则返回true，否则返回false。一般情况下会和shutdown方法组合使用。
     * 在实际使用过程中, 使用shutdown()关闭，回收资源。如果有必要，可以在其后执行shutdownNow(),取消所有遗留的任务。
     *isShutDown：当调用shutdown()或shutdownNow()方法后返回为true。 
     * isTerminated：当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
     * isTerminated：当调用shutdownNow()方法后，成功停止后返回为true;
     * 如果线程池任务正常完成，都为false
     *调用完shutdownNow和shuwdown方法后，并不代表线程池已经完成关闭操作，它只是异步的通知线程池进行关闭处理。如果要同步等待线程池彻底关闭后才继续往下执行，需要调用awaitTermination方法进行同步等待。
     *
     *线程池使用一个AtomicInteger的ctl变量将 workerCount（工作线程数量）和 runState（运行状态）两个字段压缩在一起,前三位表示状态，后29为表示数量
     * 在 Queue 中 poll()和 remove()有什么区别
     * 相同点：都是返回第一个元素，并在队列中删除返回的对象。
     * 不同点：如果没有元素 poll()会返回 null，而 remove()会直接抛出 NoSuchElementException 异常。
     *  add 和poll方法
      *  两者都是往队列尾部插入元素，不同的时候，当超出队列界限的时候，add（）方法是抛出异常让你处理，而offer（）方法是直接返回false
     */
}
