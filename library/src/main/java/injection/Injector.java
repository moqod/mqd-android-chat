package injection;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 11/03/16
 * Time: 18:27
 */
public class Injector {

    @SuppressWarnings("unchecked")
    public static <C> C getComponent(Object o, Class<C> component) {
        return component.cast(((HasComponent)o).getComponent());
    }

}
