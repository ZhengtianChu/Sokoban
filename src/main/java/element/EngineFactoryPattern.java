package element;

/**
 * I create a simple factory pattern for my game engine, this factory can
 * generate different engines through extending the abstract class {@code Engine}.
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
public class EngineFactoryPattern {
    public static Engine GenerateEngine(){
        EngineFactory factory = new EngineFactory();
        return EngineFactory.Manufacture("GameEngine");
    }
}

/**
 * The abstract class {@code Engine}.\
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
abstract class Engine {
    public abstract void ToggleDebug();
    public abstract boolean IsGameComplete ();
}

/**
 * This class is the factory of manufacturing different engines,
 * but the current product is only the {@link GameEngine}.
 *
 * @author Zhengtian CHU
 * @since 2.0
 */
class EngineFactory {
    public static Engine Manufacture(String EngineName){
        switch (EngineName){
            case "GameEngine":
                return new GameEngine();
            default:
                return null;
        }
    }
}
