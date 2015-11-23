import org.junit.Test;
import tatar.tourism.pojo.Picture;
import tatar.tourism.pojo.Point;

/**
 * Created by Ilya Evlampiev on 16.11.2015.
 */
public class PictureCoordinatesTest {

/*
Тестируем, что  при совпадении изначальной точки и точки указателя будет выставлено 0 градусов
 . 1,2
 . .
 */
    @Test
    public void testPic11_11() throws Throwable {
     Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,1f,1f,1f);
        assert(p.getDegree().equals(0d)); //Double.NaN
    }

    @Test
    public void testPic00_00() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(0f,0f,0f,0f);
        assert(p.getDegree().equals(0d)); //Double.NaN
    }

    /*
Тестируем, что  при совпадении изначальной точки и точки указателя пр широте будет выставлено 90 т -90 градусов
 1 2
 . .
 */
    @Test
    public void testPic10_11() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,0f,1f,1f);
        assert(p.getDegree().equals(90d));
    }

    /*
 2 1
 . .
     */
    @Test
    public void testPic11_10() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,1f,1f,0f);
        assert(p.getDegree().equals(-90d));
    }

    /*
Тестируем, что  при совпадении изначальной точки и точки указателя пр долготе будет выставлено 90 т -90 градусов
 . 2
 . 1

*/
    @Test
    public void testPic01_11() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(0f,1f,1f,1f);
        assert(p.getDegree().equals(0d));
    }
/*
 . 1
 . 2

 */
    @Test
    public void testPic11_01() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,1f,0f,1f);
        assert(p.getDegree().equals(180d));
    }

    /*
     . 1
     2 .

     */
    @Test
    public void testPic11_00() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,1f,0f,0f);
        assert(p.getDegree().equals(-135d));
    }

    /*
        . 2
        1 .

        */
    @Test
    public void testPic00_11() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(0f,0f,1f,1f);
        assert(p.getDegree().equals(45d));
    }

    /*
    1 .
    . 2

    */
    @Test
    public void testPic10_01() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(1f,0f,0f,1f);
        assert(p.getDegree().equals(135d));
    }

    /*
    2 .
    . 1

    */
    @Test
    public void testPic01_10() throws Throwable {
        Picture p=new Picture();
        p.setCoordinatesAndDegree(0f,1f,1f,0f);
        assert(p.getDegree().equals(-45d));
    }

    @Test
    public void testBadIdeaDegreeSetterGetter() throws Throwable {
        Picture p=new Picture();
        p.setDegree(234.24d);
        assert(p.getDegree().equals(234.24d));
    }

}
