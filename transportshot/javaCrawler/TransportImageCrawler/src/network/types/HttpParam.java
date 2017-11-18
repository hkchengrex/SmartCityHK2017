package network.types;


/**
 * Created by cheng on 27/1/2017.
 */

public class HttpParam<T> {

	T _object;
	String _first;
	
    public HttpParam(String first, T second) {
        _first = first;
        _object = second;
    }

    public String getKey(){
        return _first;
    }

    public T getValue(){
        return _object;
    }
}
