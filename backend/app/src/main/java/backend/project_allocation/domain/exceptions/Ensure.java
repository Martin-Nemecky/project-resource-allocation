package backend.project_allocation.domain.exceptions;

public class Ensure {

    public static <T> T notNull(T value, String message){
        if(value == null){
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    public static <T> T notNullElse(T value, T defaultValue){
        if(value == null){
            if(defaultValue == null){
                throw new IllegalArgumentException("Both value and default value are null");
            } else {
                return defaultValue;
            }
        }

        return value;
    }
}
