package com.poolu.poolu.service.command;

import com.poolu.poolu.model.model.Pool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PoolCommandInvoker {

    private final List<PoolCommand<? extends PoolCommandRequest>> commands;

    public PoolCommandInvoker(List<PoolCommand<? extends PoolCommandRequest>> commands) {
        this.commands = commands;
    }

    public Pool execute(PoolCommandRequest request) {
        for (PoolCommand<? extends PoolCommandRequest> command : commands) {
            if (command.requestType().isInstance(request) && supports(command, request)) {
                return execute(command, request);
            }
        }
        throw new IllegalArgumentException("No command registered for request");
    }

    @SuppressWarnings("unchecked")
    private boolean supports(PoolCommand<? extends PoolCommandRequest> command, PoolCommandRequest request) {
        return ((PoolCommand<PoolCommandRequest>) command).supports(request);
    }

    @SuppressWarnings("unchecked")
    private Pool execute(PoolCommand<? extends PoolCommandRequest> command, PoolCommandRequest request) {
        return ((PoolCommand<PoolCommandRequest>) command).execute(request);
    }
}
