package com.curiousattemptbunny.gradle.onejar

import org.gradle.api.Plugin;
import org.gradle.api.Project;

class OneJarPlugin implements Plugin<Project> {
	void apply(Project project) {
		project.OneJar = OneJar.class
	}
}
