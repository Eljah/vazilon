import org.junit.Test;
import tatar.tourism.pojo.Picture;
import tatar.tourism.pojo.Point;

/**
 * Created by Ilya Evlampiev on 16.11.2015.
 */
public class PictureCoordinatesTest {

/*
Тестируем, что  при совпадении изначальной точки и точки указателя будет выставлено 0 градусов
 */
    @Test
    public void testPic11_11() throws Throwable {
     Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,1f,1f,1f);
        assert(p.getDegree()==0);
    }

    /*
Тестируем, что  при совпадении изначальной точки и точки указателя пр широте будет выставлено 90 т -90 градусов
 */
    @Test
    public void testPic10_11() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,0f,1f,1f);
        assert(p.getDegree()==90);
    }

    @Test
    public void testPic11_10() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,1f,1f,0f);
        assert(p.getDegree()==-90);
    }

    /*
Тестируем, что  при совпадении изначальной точки и точки указателя пр долготе будет выставлено 90 т -90 градусов
*/
    @Test
    public void testPic01_11() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,0f,1f,1f);
        assert(p.getDegree()==0);
    }

    @Test
    public void testPic11_01() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,1f,1f,0f);
        assert(p.getDegree()==180);
    }


}
