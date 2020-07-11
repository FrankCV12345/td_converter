package sample.models.ExceptionsModels;

public class PublicationInvalidException extends Exception {
   public PublicationInvalidException (String msg){
       super(msg);
   }
}
