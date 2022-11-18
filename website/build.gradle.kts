plugins {
    alias(libs.plugins.pages)
    alias(libs.plugins.git.publish)
}

pages {
    resources.from("$rootDir/build/dokka/")
    styles.add("https://cdnjs.cloudflare.com/ajax/libs/prism/1.28.0/themes/prism-tomorrow.min.css")
    scripts.addAll(
        "https://cdnjs.cloudflare.com/ajax/libs/prism/1.28.0/prism.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/prism/1.28.0/components/prism-groovy.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/prism/1.28.0/components/prism-kotlin.min.js"
    )
    contents {
        add("$rootDir/rules.md", "rules.html")
    }
    minimal {
        authorName = DEVELOPER_NAME
        authorUrl = DEVELOPER_URL
        projectName = RELEASE_ARTIFACT
        projectDescription = RELEASE_DESCRIPTION
        projectUrl = RELEASE_URL
        button("View\nDocumentation", "dokka")
    }
}

gitPublish {
    repoUri.set("git@github.com:$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
    branch.set("gh-pages")
    contents.from(pages.outputDirectory)
}

tasks {
    register(LifecycleBasePlugin.CLEAN_TASK_NAME) {
        delete(buildDir)
    }
    deployPages {
        dependsOn(":dokkaHtmlMultiModule")
    }
}
