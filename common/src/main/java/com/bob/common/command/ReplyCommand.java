package com.bob.common.command;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

public class ReplyCommand<T> {

    private Action0 execute0;
    private Action1<T> execute1;

    private Func0<Boolean> canExecute0;

    public ReplyCommand(Action0 execute) {
        this.execute0 = execute;
    }

    public ReplyCommand(Action1<T> execute) {
        this.execute1 = execute;
    }

    public ReplyCommand(Action0 execute, Func0<Boolean> canExecute0) {
        this.execute0 = execute;
        this.canExecute0 = canExecute0;
    }

    public ReplyCommand(Action1<T> execute, Func0<Boolean> canExecute0) {
        this.execute1 = execute;
        this.canExecute0 = canExecute0;
    }

    public void execute() {
        if (execute0 != null && canExecute0()) {
            execute0.call();
        }
    }

    private boolean canExecute0() {
        if (canExecute0 == null) {
            return true;
        }
        return canExecute0.call();
    }

    public void execute(T parameter) {
        if (execute1 != null && canExecute0()) {
            execute1.call(parameter);
        }
    }

}
