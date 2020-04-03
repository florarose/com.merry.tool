package vedio;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.ApiError;
import cn.ucloud.ufile.api.bucket.BucketType;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.api.object.PutFileApi;
import cn.ucloud.ufile.api.object.policy.PolicyParam;
import cn.ucloud.ufile.api.object.policy.PutPolicy;
import cn.ucloud.ufile.api.object.policy.PutPolicyForCallback;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.bean.BucketResponse;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.bean.UfileErrorBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import cn.ucloud.ufile.http.UfileCallback;
import cn.ucloud.ufile.util.JLog;
import cn.ucloud.ufile.util.MimeTypeUtil;
import cn.ucloud.ufile.util.StorageType;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import okhttp3.Request;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.URLEncoder;
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
}
