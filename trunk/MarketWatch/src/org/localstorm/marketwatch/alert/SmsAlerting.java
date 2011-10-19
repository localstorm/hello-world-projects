package org.localstorm.marketwatch.alert;


public class SmsAlerting implements Alerting {
    public void sendMessage(String msg) {
        boolean success = false;
        do {
            try {
                SmsNotificationUtil.notify(msg);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!success);
    }
}
