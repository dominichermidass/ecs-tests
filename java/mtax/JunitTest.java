import static org.junit.Assert.*;
import org.junit.Test;

 public class JunitTest{
    
    //No taxes
    @test
    public void taxtListEmptyOrNull(){

        String result = Mtx.validate(null).get(0);

        String expected = "The document has no fees";

        assertEquals(expected,result);
    }

    // has no amount but valid id
    @test
    public void hasNoAmount(){


        X_Tax taxDummy = new X_Tax();
        int id  =  5;
        String expected = "The tax is mandatory in id: "+id;
        taxDummy.setId(id);
        List<X_Tax> taxList = new List<X_Tax>();
        taxList.add(taxDummy);
        String result = Mtx.validate(taxList).get(0);
        
        assertEquals(expected,result);

    }
    
} 