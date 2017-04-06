package vinsoft.com.wavefindyourfriend.model;

/**
 * Created by DONG on 05-Apr-17.
 */

public class Message  {
    String personSendID;
    String contentMessage;
    String messageVision;//trangj thai xem chua

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    String dateTime;

    public String getPersonSendID() {
        return personSendID;
    }

    public void setPersonSendID(String personSendID) {
        this.personSendID = personSendID;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }


    public String getMessageVision() {
        return messageVision;
    }

    public void setMessageVision(String messageVision) {
        this.messageVision = messageVision;
    }


}
