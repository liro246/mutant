package co.com.meli.jpa.helper;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;
import static reactor.core.publisher.Mono.fromSupplier;

public abstract class AdapterOperations<E, D, I, R extends CrudRepository<D, I> & QueryByExampleExecutor<D>> {
    protected R repository;
    private Class<D> dataClass;
    protected ObjectMapper mapper;
    private Function<D, E> toEntityFn;

    public AdapterOperations(R repository, ObjectMapper mapper, Function<D, E> toEntityFn) {
        this.repository = repository;
        this.mapper = mapper;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        this.toEntityFn = toEntityFn;
    }

    protected D toData(E entity) {
        return mapper.map(entity, dataClass);
    }

    protected E toEntity(D data) {
        return data != null ? toEntityFn.apply(data) : null;
    }

    public Mono<E> save(E entity) {
        return Mono.just(entity)
                .map(this::toData)
                .flatMap(this::saveData)
                .thenReturn(entity);
    }

    protected List<E> saveAllEntities(List<E> entities) {
        List<D> list = entities.stream().map(this::toData).collect(Collectors.toList());
        return toList(saveData(list));
    }

    public List<E> toList(Iterable<D> iterable) {
        return stream(iterable.spliterator(), false).map(this::toEntity).collect(Collectors.toList());
    }

    protected Mono<D> saveData(D data) {
        return fromSupplier(() -> repository.save(data))
                .subscribeOn(Schedulers.boundedElastic());
    }

    protected Iterable<D> saveData(List<D> data) {
        return repository.saveAll(data);
    }

    public E findById(I id) {
        return toEntity(repository.findById(id).orElse(null));
    }

    public List<E> findByExample(E entity) {
        return toList(repository.findAll(Example.of(toData(entity))));
    }

    public Flux<E> findAll() {
        return doQueryMany(() -> repository.findAll());
    }

    public Mono<E> doQuery(Supplier<D> query) {
        return doQuery(Mono.just(query.get())).subscribeOn(Schedulers.boundedElastic()).flatMap(Mono::justOrEmpty);
    }

    private Mono<E> doQuery(Mono<D> query) {
        return query.map(this::toEntity);
    }
    protected Flux<E> doQueryMany(Supplier<Iterable<D>> query) {
        return fromSupplier(query).subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(this::toEntity);
    }
}
