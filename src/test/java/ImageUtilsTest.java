import com.zouyu.Utils.ImageUtils;
import org.junit.Test;

import java.awt.*;

public class ImageUtilsTest {
  
    /**测试图片合并*/  
    @Test
    public void testMergeImage(){  
        ImageUtils.mergeImage("D:/source/1.jpg", "D:/source/2.png", "D:/source/3.png");
    }  
    /**测试添加图片水印*/  
    @Test  
    public void testAddImageWeatermark(){  
        ImageUtils.addImageWeatermark("D:/source/1.jpg", "D:/source/2.jpg", -1, -1, 1f);
    }  
    /**测试添加文字水印*/  
    @Test  
    public void testAddTextWeatermark(){  
        ImageUtils.addTextWeatermark("D:/source/1.jpg", "添加文本水印", "宋体", Font.BOLD | Font.ITALIC, 20, Color.BLACK, 100, 100, 8f);
    }  
    /**图片缩放*/  
    @Test  
    public void testResize(){  
        ImageUtils.resize("D:/source/1.jpg", 1000, 500, true);
    }  
}  