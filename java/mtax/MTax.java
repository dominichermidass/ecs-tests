import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {
    
    public MTax(){
        
    }
    
    public static List<String> validate(List<X_Tax> xTaxList) {
        
        List<String> errorList = new ArrayList<>();
        
        List<String> taxCategoryList = MInfoTaxCategory.getTaxCategoryStringList();

        //checking is taxList is empty or null
        if(xTaxList != null && xTaxList.size() > 0) {
            
            List<String> validIds = new ArrayList<>();

            // is there any foreign Rates? to be charge?
            boolean foreignRates = false;

            // Using id to display at input
            String id = "";

            for (X_Tax tax : xTaxList) {
                
                 id = tax.getId().toString();
                
                //tax has id?
                if(tax.getId() != null){

                    validIds.add(tax.getId().toString());
                    //tax has amount?
                      if(tax.getAmount() == null) {

                      errorList.add("The amount is mandatory in id: "+id);

                  }//end if
                
                 //taxt has content?
                if(tax.getTax() == null) {
                    errorList.add("The tax is mandatory in id: "+id);

                }else if(!taxCategoryList.contains(tax.getTax())) {
                      
                      errorList.add("The tax is not a valid data in id: "+id);

                  }//end else

                  if(tax.isLocal()){

                      if(tax.isTrasladado() && tax.getTaxAmount() == null ) {
                          
                          errorList.add("the TaxAmount is mandotory in id: "+id);

                      }//end if

                  } //end if
                  else {
                      if(tax.getTaxAmount() == null ) {
                          errorList.add("The amount is mandatory in id: "+id);

                      }//end if

                  }//end else
                

                }//end if

                //tax is foreign                
                if(!tax.isLocal()){

                    foreignRates = true;

                }//end if

            }//end for

            //theres no foreignRates so..
            if(!foreignRates){

                errorList.add("Must include at least one non-local rate/tax");

            }//end if
            if(validIds.size() > 0){
                    
                    List<X_Tax> xt = TaxsByListId(validIds, false);

                    if(xt.size() != validIds.size()){

                        errorList.add("There are data not saved previously");

                    }else{
                        HashMap<String, X_Tax> map_taxs = new HashMap<String, X_Tax>();

                        for(X_Tax tax: xt){

                            map_taxs.put(tax.getId().toString(), tax);

                        }//end for

                        for(int i = 0; i < xTaxList.size(); i++){

                            if(xTaxList.get(i).getId() != null){

                                xTaxList.get(i).setCreated(map_taxs.get(xTaxList.get(i).getId().toString()).getCreated());
                                                
                            }//end if

                        }//end for

                    }//end else

            }//end if
            
        }//end if
        else {
            errorList.add("The document has no fees");
        }//end else
        
        return errorList;
    }
    
}
