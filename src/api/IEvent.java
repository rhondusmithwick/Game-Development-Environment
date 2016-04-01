package api;

public interface IEvent<L> {
    void notify (final L listener); // why use final?
}
