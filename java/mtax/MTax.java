import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {
    
    public MTax(){
        
    }
    
    public static List<String> validate(List<X_Tax> xTaxList) {
        
        List<String> errorList = new ArrayList<>();
        
        List<String> taxCategoryList = MInfoTaxCategory.getTaxCategoryStringList();
        
        if(xTaxList != null && xTaxList.size() > 0) {
            
            List<String> validIds = new ArrayList<>();
            int cont = 0;

            for (X_Tax tax : xTaxList) {
                if(tax.getId() != null){
                    validIds.add(tax.getId().toString());
                }
                  if(tax.getAmount() == null) {
                      errorList.add("The amount is mandatory");
                  }

                if(tax.getTax() == null) {
                    errorList.add("The tax is mandatory");
                }
                  else if(!taxCategoryList.contains(tax.getTax())) {
                      errorList.add("The tax is not a valid data");
                  }

                  if(tax.isLocal()){
                      if(tax.isTrasladado() && tax.getTaxAmount() == null ) {
                          errorList.add("the TaxAmount is mandotory");
                      }
                  } 
                  else {
                      if(tax.getTaxAmount() == null ) {
                          errorList.add("The amount is mandatory");
                      }
                  }
                
                if(!tax.isLocal()){
                    cont++;
                }
            }
            if(cont<=0){
                errorList.add("Must include at least one non-local rate/tax");
            }
            if(validIds.size() > 0){
                    
                    List<X_Tax> xt = TaxsByListId(validIds, false);
                    if(xt.size() != validIds.size()){
                        errorList.add("There are data not saved previously");
                    }else{
                        HashMap<String, X_Tax> map_taxs = new HashMap<String, X_Tax>();
                        for(X_Tax tax: xt){
                            map_taxs.put(tax.getId().toString(), tax);
                        }
                        for(int i = 0; i < xTaxList.size(); i++){
                            if(xTaxList.get(i).getId() != null){
                                xTaxList.get(i).setCreated(
                                        map_taxs.get(xTaxList.get(i).getId().toString())
                                                .getCreated());
                            }
                        }
                    }
            }
        }
        else {
            errorList.add("The document has no fees");
        }
        
        return errorList;
    }
    
}
