package com.lz.framework.websocket.core.sender.local;

import com.lz.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import com.lz.framework.websocket.core.sender.WebSocketMessageSender;
import com.lz.framework.websocket.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author YY
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
