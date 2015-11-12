package tatar.tourism.web.websocket;

import org.apache.log4j.Logger;
import tatar.tourism.dao.MySqlNotificationDao;
import tatar.tourism.dao.NotificationDao;
import tatar.tourism.pojo.Notification;
import tatar.tourism.pojo.User;

import java.io.IOException;

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/websocket/chat", configurator = GetHttpSessionConfigurator.class)
public class NotificationWebsocket {
    private final NotificationDao notificationDao = new MySqlNotificationDao();
    private HttpSession httpSession;
    private User endpointUser;
    private String endpointUserName;
    private boolean notStopFlag = true;

    static Logger log = Logger.getLogger(NotificationWebsocket.class);

    private static final Set<NotificationWebsocket> connections =
            new CopyOnWriteArraySet<NotificationWebsocket>();
    private static final Set<User> httpUserConnections =
            new CopyOnWriteArraySet<User>();
    private Session session;

    @OnOpen
    public void start(Session session, EndpointConfig config) {
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        connections.add(this);
        endpointUser = ((User) httpSession.getAttribute("user"));
        endpointUserName = ((User) httpSession.getAttribute("user")).getUsername();

        if (!httpUserConnections.contains(this.endpointUser)) {
            httpUserConnections.add(this.endpointUser);
            log.info("New user is logged in; associating the new http session with websocket session");
            if (endpointUser != null) {
                log.info("Obtaining user object from the session");
                checkStoredNotificationsAndBroadcastThem(endpointUser);
            }
            checkNewNotificationsAndBroadcastThem();
        } else {
            log.info("Previously logged user refreshes the page; associating the already existing http session with websocket session");
            checkNewNotificationsAndBroadcastThem();
        }
    }


    @OnClose
    public void end() {
        log.info("Closing connection to user " + endpointUserName);
        httpUserConnections.remove(endpointUser);
        connections.remove(this);
        notStopFlag = false;
    }


    @OnMessage
    public void incoming(String message) {
    }


    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("Chat Error: " + t.toString(), t);
        notStopFlag = false;
    }

    private void send(Notification ntf) {
        boolean sentFlag = false;
        for (NotificationWebsocket client : connections) {
            if (client.endpointUser.getDatabaseId() == (ntf.getAddressee().getDatabaseId())) {
                try {
                    synchronized (client) {
                        client.session.getBasicRemote().sendText("<i class=\"fa fa-envelope\"></i> "+ntf.getText());
                        sentFlag = true;
                    }
                } catch (IOException e) {
                    log.error("Failed to send message to client", e);
                    connections.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                        // Ignore
                    }
                }
            }
        }
        if (!sentFlag) {
            notificationDao.create(ntf);
        }
    }

    private void checkStoredNotificationsAndBroadcastThem(User user) {
        List<Notification> notificationList;
        notificationList = notificationDao.readAllForUser(user);
        notificationDao.deleteAllForUser(user);
        for (Notification notification : notificationList) {
            send(notification);
        }
        log.trace("The stored in DB notification was sent to " + user.getUsername());
    }

    private void checkNewNotificationsAndBroadcastThem() {
        log.info("New notifications sending run is started");
        Thread thread = new Thread() {
            @Override
            public void run() {
                Queue<Notification> notificationQueue = Notification.queue;
                while (notStopFlag) {
                    log.trace("New checking cycle is started");
                    Notification notification = notificationQueue.poll();
                    if (notification != null) {
                        log.info("Websocket deals with user " + notification.getAddressee().getUsername());
                        send(notification);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}