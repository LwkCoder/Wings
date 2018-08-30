package com.lwkandroid.wingsdemo.project.test;

import com.lwkandroid.wings.utils.ToastUtils;
import com.socks.library.KLog;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by LWK
 * TODO
 */

public class UDPReceivedThread extends Thread
{
    private static final String TAG = "UDPReceivedThread";
    private String ip;
    private int port;
    private boolean stop;
    private DatagramSocket mSocket;

    public UDPReceivedThread(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    public void resumeSend()
    {
        this.stop = false;
    }

    public void pauseSend()
    {
        this.stop = true;
    }

    public void onDestory()
    {
        pauseSend();
        if (mSocket != null && !mSocket.isClosed())
        {
            if (mSocket.isConnected())
                mSocket.disconnect();
            mSocket.close();
        }
        mSocket = null;
    }

    private void initSocket()
    {
        if (mSocket != null && !mSocket.isClosed())
            return;
        try
        {
            InetAddress address = InetAddress.getByName(ip);
            mSocket = new DatagramSocket(port, address);
        } catch (Exception e)
        {
            e.printStackTrace();
            KLog.e(TAG, "Create socket failed:" + e.toString());
            ToastUtils.showLong("Create socket failed:" + e.toString());
        }
    }

    @Override
    public void run()
    {
        super.run();
        initSocket();
        while (!stop)
        {
            try
            {
                byte datas[] = new byte[1024];
                DatagramPacket packet = new DatagramPacket(datas, datas.length);
                if (mSocket != null && !mSocket.isClosed())
                    mSocket.receive(packet);
                String receiveMsg = new String(packet.getData(), "utf-8").trim();
                KLog.e("接收到消息：" + receiveMsg);
            } catch (IOException e)
            {
                KLog.e(TAG, "无法接收消息：" + e.toString());
            }
        }
    }
}
