package org.zk.app.security;

import org.zk.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
