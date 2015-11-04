package com.imzhitu.admin.scheduler;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class CommentBroadcastSchedulerTest extends BaseTest {

	@Autowired
	private CommentBroadcastScheduler sc;
	
	@Test
	public void broadcastCommentQueueTest() {
		sc.broadcastCommentQueue();
	}
}
