package com.xh.networkspeed;

import android.net.TrafficStats;

public class NetworkSpeed {
    private static final String TAG = NetworkSpeed.class.getSimpleName();
    private double lastTotalRxBytes = 0;
    private double lastTimeStamp = 0;

    private double lastDownLoadBytes = 0;
    private double lastUpLoadBytes = 0;
    private double lastDownLoadBytesUid = 0;
    private double lastUpLoadBytesUid = 0;

    private double lastDownLoadTimeStamp = 0;
    private double lastUpLoadTimeStamp = 0;
    private double lastUpLoadTimeStampUid = 0;
    private double lastDownLoadTimeStampUid = 0;

    public NetSpeedResult getNetSpeed(int uid) {
        double downLoadSpeed = getTotalRxBytes();
        double upLoadSpeed = getTotalTxBytes();
        double downLoadSpeedUid = getTotalRxBytesByUid(uid);
        double upLoadSpeedUid = getTotalTxBytesByUid(uid);
        NetSpeedResult result = new NetSpeedResult();
        result.setDownLoadSpeed(String.valueOf(downLoadSpeed));
        result.setDownLoadSpeedUid(String.valueOf(downLoadSpeedUid));
        result.setUpLoadSpeed(String.valueOf(upLoadSpeed));
        result.setUpLoadSpeedUid(String.valueOf(upLoadSpeedUid));

        return result;
    }


    // 获取手机所有接收流量 // returns as kb/s
    public double getTotalRxBytes() {
        double nowTimeStamp = System.currentTimeMillis();
        double nowTotalRxBytes = TrafficStats.getTotalRxBytes() == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes());//转为KB
        double speed = (((nowTotalRxBytes - lastDownLoadBytes) * 8) / ((nowTimeStamp - lastDownLoadTimeStamp) / 1000)) / 1024;//毫秒转换 //bits/second
        lastDownLoadTimeStamp = nowTimeStamp;
        lastDownLoadBytes = nowTotalRxBytes;
        return speed;
    }

    // 获取手机指定进程的接收流量 // returns as kb/s
    public double getTotalRxBytesByUid(int uid) {
        double nowTimeStamp = System.currentTimeMillis();
        double nowTotalRxBytes = TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getUidRxBytes(uid));//转为KB
        double speed = (((nowTotalRxBytes - lastDownLoadBytesUid) * 8) / ((nowTimeStamp - lastDownLoadTimeStampUid) / 1000)) / 1024;//毫秒转换
        lastDownLoadTimeStampUid = nowTimeStamp;
        lastDownLoadBytesUid = nowTotalRxBytes;
        return speed;

    }

    // 获取指定进程的发送流量
    public double getTotalTxBytesByUid(int uid) {
        double nowTimeStamp = System.currentTimeMillis();
        double nowTotalTxBytes = TrafficStats.getUidTxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getUidTxBytes(uid) / 1024);//转为KB
        double speed = ((nowTotalTxBytes - lastUpLoadBytesUid) * 1000 / (nowTimeStamp - lastUpLoadTimeStampUid));//毫秒转换
        lastUpLoadTimeStampUid = nowTimeStamp;
        lastUpLoadBytesUid = nowTotalTxBytes;
        return speed;
    }

    // 获取手机所有的发送流量
    public double getTotalTxBytes() {
        double nowTimeStamp = System.currentTimeMillis();
        double nowTotalTxBytes = TrafficStats.getTotalTxBytes() == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalTxBytes() / 1024);//转为KB
        double speed = ((nowTotalTxBytes - lastUpLoadBytes) * 1000 / (nowTimeStamp - lastUpLoadTimeStamp));//毫秒转换
        lastUpLoadTimeStamp = nowTimeStamp;
        lastUpLoadBytes = nowTotalTxBytes;
        return speed;
    }

    // 结果对象
    public class NetSpeedResult {
        private String downLoadSpeed = "";
        private String upLoadSpeed = "";
        private String downLoadSpeedUid = "";
        private String upLoadSpeedUid = "";

        public void setDownLoadSpeed(String downLoadSpeed) {
            this.downLoadSpeed = downLoadSpeed;
        }

        public void setDownLoadSpeedUid(String downLoadSpeedUid) {
            this.downLoadSpeedUid = downLoadSpeedUid;
        }

        public void setUpLoadSpeed(String upLoadSpeed) {
            this.upLoadSpeed = upLoadSpeed;
        }

        public void setUpLoadSpeedUid(String upLoadSpeedUid) {
            this.upLoadSpeedUid = upLoadSpeedUid;
        }

        public String getDownLoadSpeed() {
            return downLoadSpeed;
        }

        public String getDownLoadSpeedUid() {
            return downLoadSpeedUid;
        }

        public String getUpLoadSpeed() {
            return upLoadSpeed;
        }

        public String getUpLoadSpeedUid() {
            return upLoadSpeedUid;
        }
    }
}
