package sinolight.cn.qgapp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import java.util.Random;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/6/30.
 * 根据字符串生成图片验证码
 */

public class VCodeUtil {

    /**
     * 生成验证码图片
     *
     * @param width
     * @param height
     * @param size
     *            字体大小(以sp为单位计算)
     * @param scale
     *            缩放系数（DisplayMetrics类中属性scaledDensity）
     * @param securityCode
     * @return
     */
    public static Bitmap createSecurityCodeBitmap(int width, int height, int size,
                                           float scale, String securityCode) {

        int textSize = ScreenUtil.sp2px(size, scale);

        Bitmap codeBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(codeBitmap);
        canvas.drawColor(App.getContext().getResources().getColor(R.color.color_white));

        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setStrokeWidth(3);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        int x = (width - textSize * 3) / 2;
        int y = (height + textSize) / 2;
        for (int index = 0; index < securityCode.length(); index++) {
            textPaint.setColor(ContextCompat.getColor(App.getContext(), R.color.color_register_vcode));
            canvas.drawText(securityCode.charAt(index) + "", (x + textSize
                    * index), y, textPaint);
        }
        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            textPaint.setStrokeWidth(2);
            textPaint.setColor(randomColor(1));
            canvas.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height), textPaint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return codeBitmap;
    }

    private static int randomColor(int rate) {
        Random random = new Random();
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }
}
