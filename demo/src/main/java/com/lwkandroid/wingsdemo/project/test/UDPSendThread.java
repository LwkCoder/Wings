package com.lwkandroid.wingsdemo.project.test;

import com.lwkandroid.wings.utils.ToastUtils;
import com.socks.library.KLog;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWK
 * TODO
 */

public class UDPSendThread extends Thread
{
    private static final String TAG = "UDPSendThread";
    private String ip;
    private int port;
    private boolean stop;
    private DatagramSocket mSocket;
    private List<String> mMessageList = new ArrayList<>();

    public UDPSendThread(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    public void addMessage(String message)
    {
        mMessageList.add(message);
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
            mSocket = new DatagramSocket();
        } catch (SocketException e)
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
            if (mMessageList == null || mMessageList.size() == 0)
                continue;

            String message = mMessageList.get(0);
            if (message == null)
                continue;

            try
            {
                byte[] datas = message.getBytes("utf-8");
                InetAddress address = InetAddress.getByName(ip);
                DatagramPacket packet = new DatagramPacket(datas, datas.length, address, port);
                if (mSocket != null && !mSocket.isClosed())
                    mSocket.send(packet);
                mMessageList.remove(0);
                KLog.e(TAG, "消息已发送：" + message);
            } catch (Exception e)
            {
                e.printStackTrace();
                KLog.e(TAG, "Send message failed:\nmessage:" + message + "\nexception:" + e.toString());
                ToastUtils.showLong("Send message failed:\nmessage:" + message + "\nexception:" + e.toString());
            }
        }
    }

}
