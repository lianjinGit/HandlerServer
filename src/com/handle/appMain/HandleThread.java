package com.handle.appMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;

import com.google.gson.Gson;
import com.handle.domain.User;
import com.handle.util.ErrorCodeMapping;
import com.handle.util.MsgBase;
import com.handle.util.RequestResponseCodeMapping;
import com.handle.util.response.LonginResponse;
import com.handle.util.response.RegisterResponse;

public class HandleThread extends Thread {

    private SocketChannel socketChannel;

    private boolean isRunning = false;
    private BufferedWriter writer;
    private BufferedReader reader;

    public HandleThread(SocketChannel socketChannel) {
        super();
        this.socketChannel = socketChannel;
        isRunning = true;

        writer = new BufferedWriter(Channels.newWriter(this.socketChannel, "UTF-8"));

        reader = new BufferedReader(Channels.newReader(this.socketChannel, "UTF-8"));
    }

    /**
     * Sends a message.
     */
    public void sendMessage(String pMessage) throws java.io.IOException {
        writer.write(pMessage);
        writer.newLine();
        writer.flush();
    }

    /**
     * Gets a response from the (already open) connection.
     */
    public String retrieveResponse() throws java.io.IOException {
        String returnValue = reader.readLine();
        return returnValue;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                String returnMsg = retrieveResponse();
                MsgBase mBase = new Gson().fromJson(returnMsg, MsgBase.class);
                if (mBase.getType() == RequestResponseCodeMapping.LOGINREQUESTCODE) {
                    doLongin(returnMsg);
                }else  if (mBase.getType() == RequestResponseCodeMapping.REGISTER_REQUESTCODE) {
                    doRegister(returnMsg);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }

        }
        if (writer != null) {
            try {
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException e) {

                e.printStackTrace();

            }
        }
        if (socketChannel.isConnected()) {
            try {
                socketChannel.close();
            }
            catch (IOException e) {
                e.printStackTrace();

            }
        }

    }

    private void doLongin(String recieveMsg) {
        User user = (User) new User().initFromJson(recieveMsg);
        user.load();
        LonginResponse loginResponse = new LonginResponse();
        loginResponse.setUser(user.getUser());
        if (!user.isFound) {
            loginResponse.setReturCode(ErrorCodeMapping.ERROR_CODE_USERNAMEPASSWORD_ERROR);
        }
        else {
            loginResponse.setReturCode(ErrorCodeMapping.NO_ERROR);
        }
        try {
            sendMessage(new Gson().toJson(loginResponse));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doRegister(String recieveMsg) {
        User user = (User) new User().initFromJson(recieveMsg);
        RegisterResponse registerResponse = new RegisterResponse();
        User _user = new User();
        _user.setUserId(user.getUserId());
        _user.loadByUserId();
        if (_user.isFound) {
            registerResponse.setReturCode(ErrorCodeMapping.ERROR_DUPLICATED);
        }
        else {
            int checkCode = user.check();
            if (checkCode != ErrorCodeMapping.NO_ERROR) {
                registerResponse.setReturCode(checkCode);
            }
            else {
                int ret = user.save();
                if (ret != 1) {
                    registerResponse.setReturCode(ErrorCodeMapping.ERROR_REGISTER);
                }
                else {
                    registerResponse.setReturCode(ErrorCodeMapping.NO_ERROR);
                }
            }
        }
        registerResponse.setUser(user.getUser());
        try {
            sendMessage(new Gson().toJson(registerResponse));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
