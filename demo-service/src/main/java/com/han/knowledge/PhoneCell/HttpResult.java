package com.han.knowledge.PhoneCell;


/**
 * @author hanyf
 */
public class HttpResult {

    private String errno;
    private String message;
    private String data;

    /**
     * @return the errno
     */
    public String getErrno() {

        return errno;
    }

    /**
     * @param errno the errno to set
     */
    public void setErrno(String errno) {

        this.errno = errno;
    }

    /**
     * @return the message
     */
    public String getMessage() {

        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * @return the data
     */
    public String getData() {

        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {

        this.data = data;
    }


}
