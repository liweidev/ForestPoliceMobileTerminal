package com.yhkj.yhsx.forestpolicemobileterminal.utils;


import android.content.Context;
import android.widget.ImageView;

public class NetworkConnections {

	private static NetworkConnections connection;
	
//	private FinalHttp fHttp;
//	private FinalBitmap fBitmap;
	private Context context;
	
	/**
	 * 
	 * @param context
	 */
	private NetworkConnections(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		/*if (fHttp == null) {
			fHttp = new FinalHttp();
			fHttp.configTimeout(1000*30);
		}
		if (fBitmap == null) {
			fBitmap = FinalBitmap.create(context);
			fBitmap.configDiskCachePath( context.getExternalCacheDir().getPath()+"/wealthCache");
			fBitmap.configBitmapLoadThreadSize(3);
			fBitmap.configMemoryCacheSize(5);
			fBitmap.configDiskCacheSize(10*1024*1024);
		}*/
	}
	
	/**
	 * NetworkConnections初始化，创建NetworkConnections对象、FinalHttp对象、FinalBitmap对象并设置相关属性
	 * @param context
	 * @return
	 */
	public static NetworkConnections init(Context context){
		if (connection == null) {
			connection = new NetworkConnections(context);
		}
		return connection;
	}
	
	/**
	 * 调用网络接口
	 * @param
	 * @param 		           接口URL（包含接口域名或IP地址、方法名）
	 * @param params		参数列表（AjaxParams类型）
	 * @param ajaxCallback	接口回调函数（接口所有返回值均在此）
	 * @param method        "Users/Get_users"
	 */
	/*public void callNetworkInterfaceByPost(Context ct ,String method , AjaxParams params , MyAjaxCallback ajaxCallback){
//		fHttp = new FinalHttp();
		if (!NetUtil.isNetworkAvalibleService(context)) {
//			Toast.makeText(context,
//					"您的网络已经断开，请检查网络!", Toast.LENGTH_LONG)
//					.show();
			
//			Intent intent = new Intent(Settings.ACTION_SETTINGS);// ACTION_SETTINGS 
//			context.startActivity(intent);
//			return;	
		}
		String url = ActivityUtils.getServerApi(ct) +"WebApi/api/"+ method;
		//ToastUtils.show(ct, "登录操作http请求地址url: "+url);
		//String url = ActivityUtils.getServerApi(ct) +"api/"+ method;
		FinalHttp fHttp = new FinalHttp();
		fHttp.configTimeout(1000*30);
		fHttp.post(url, params, ajaxCallback);
	}*/
	
	/**
	 * 设置没有默认图片的ImageView图片地址
	 * @param
	 * @param imageView			想要设置的ImageView控件
	 * @param imageUrl				图片URL地址（可以是本地路径）
	 */
/*	public void setImageUrl(ImageView imageView, String imageUrl){
//		fBitmap = FinalBitmap.create(context);
		if (fBitmap == null) {
			fBitmap = FinalBitmap.create(context);
			fBitmap.configDiskCachePath( context.getExternalCacheDir().getPath()+"/wealthCache");
			fBitmap.configBitmapLoadThreadSize(3);
			fBitmap.configMemoryCacheSize(5);
			fBitmap.configDiskCacheSize(10*1024*1024);
		}
		fBitmap.display(imageView, imageUrl);
	}*/
		
	/**
	 * 设置ImageView图片地址
	 * @param
	 * @param imageView			想要设置的ImageView控件
	 * @param imageUrl				图片URL地址（可以是本地路径）
	 * @param 		图片在加载过程中的图片ResoureID
	 * @param imageFailId			图片加载失败显示的图片ResoureID
	 */
	/*public void setImageUrl(ImageView imageView, String imageUrl,int imageLoadingId,int imageFailId){
//		fBitmap = FinalBitmap.create(context);
		if (fBitmap == null) {
			fBitmap = FinalBitmap.create(context);
			fBitmap.configDiskCachePath(context.getExternalCacheDir().getPath()+"/forest");
			fBitmap.configBitmapLoadThreadSize(3);
			fBitmap.configMemoryCacheSize(5);
			fBitmap.configDiskCacheSize(10*1024*1024);
		}
		if (imageLoadingId != 0) {
			fBitmap.configLoadingImage(imageLoadingId);
		}
		if (imageFailId != 0) {
			fBitmap.configLoadfailImage(imageFailId);
		}
		fBitmap.display(imageView, imageUrl);
	}*/
		
	/**
	 * 设置带宽度和高度和ImageView图片地址
	 * @param
	 * @param imageView			想要设置的ImageView控件
	 * @param imageUrl				图片URL地址（可以是本地路径）
	 * @param maxWidth			图片最大宽度
	 * @param maxHeight			图片最大高度
	 * @param
	 * @param imageFailId			图片加载失败显示的图片ResoureID
	 */
	public void setImageUrl(ImageView imageView, String imageUrl, int maxWidth, int maxHeight, int imageLoadingId, int imageFailId){
//		fBitmap = FinalBitmap.create(context);
		/*if (fBitmap == null) {
			fBitmap = FinalBitmap.create(context);
			fBitmap.configDiskCachePath( context.getExternalCacheDir().getPath()+"/forest");
			fBitmap.configBitmapLoadThreadSize(3);
			fBitmap.configMemoryCacheSize(5);
			fBitmap.configDiskCacheSize(10*1024*1024);
		}
		fBitmap.configBitmapMaxHeight(maxWidth);
		fBitmap.configBitmapMaxWidth(maxHeight);
		if (imageLoadingId != 0) {
			fBitmap.configLoadingImage(imageLoadingId);
		}
		if (imageFailId != 0) {
			fBitmap.configLoadfailImage(imageFailId);
		}
		imageView.setTag(imageUrl);
		fBitmap.display(imageView, imageUrl);*/
	}
	
}
