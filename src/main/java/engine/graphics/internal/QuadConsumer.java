package engine.graphics.internal;

public interface QuadConsumer<T, U, V, W> {
    void accept(T t, U u, V v, W w);
}
