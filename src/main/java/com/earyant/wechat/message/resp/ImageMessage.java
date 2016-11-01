package com.earyant.wechat.message.resp;

/**
 * ClassName: ImageMessage
 *
 * @author dapengniao
 * @Description: 图片消息
 * @date 2016年3月8日 下午5:58:10
 */
public class ImageMessage extends BaseMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }


}
