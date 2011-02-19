package com.curiousattemptbunny.gradle.onejar

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.bundling.Jar;

class OneJar extends Jar {
	@Input
	String mainClass
	// TODO @Input @Optional
	Configuration runtime
	// TODO @Input @Optional
	Jar mainJar
	
	OneJar() {
		// default settings		
		mainJar = project.tasks.jar
		runtime = project.configurations.runtime

		// more defaults		
		configure {
			appendix = 'onejar'
			manifest {
			    	attributes  'Created-By':'Gradle OneJar task',
					    'Main-Class':'com.simontuffs.onejar.Boot'
			}
		}

		// post-configuration action
		copyAction.rootSpec.addChild().into('') {
			from { project.sourceSets.thirdparty.classes }
		}

		// post-configuration action
		copyAction.rootSpec.addChild().into('main') {
			from { mainJar.archivePath }
			rename { 'main.jar' }
		}

		// post-configuration action
		copyAction.rootSpec.addChild().into('lib') {
			from { runtime }
		}
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass
		configure {
			manifest {
				attributes 'One-Jar-Main-Class': mainClass
			}
		}	
	}

	public void setMainJar(Jar mainJar) {
		this.mainJar = mainJar
		this.dependsOn mainJar // TODO this needs to work for the default settings too
	}
}
