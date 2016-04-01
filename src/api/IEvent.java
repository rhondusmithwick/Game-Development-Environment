package api;

import java.io.Serializable;


public interface IEvent<L> extends Serializable {
    void notify (final L listener); // why use final?
}
