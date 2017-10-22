/*
 * Copyright (c) 2007-2017 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.swt.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.events.SelectionEvent;

import de.carne.Application;

/**
 * Test user agent.
 */
class TestUserController {

	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	private final TestUserInterface ui;

	TestUserController(TestUserInterface ui) {
		this.ui = ui;
	}

	void onShellDisposed() {
		this.executorService.shutdownNow();
		Application.getMain(TestUserApplicationMain.class).setStatus(0);
	}

	void onShellActivated() {
		this.executorService.schedule(this::runBackgroundCommand, 500, TimeUnit.MILLISECONDS);
	}

	private void runBackgroundCommand() {
		Application.getMain(TestUserApplicationMain.class)
				.runWait(() -> this.ui.setStatus("Background task finished."));
	}

	void onCommandItemSelected(SelectionEvent event) {
		this.ui.setStatus("Command item '" + event.widget + "'selected.");
	}

}