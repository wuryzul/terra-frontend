import com.github.gradle.node.pnpm.task.PnpmTask

plugins {
  id("io.wury.terra.gradle.terra-node")
}

val pnpmBuild = tasks.register<PnpmTask>("pnpmBuild") {
  dependsOn("nodeSetup", "pnpmSetup")

  inputs.dir("public")
  inputs.dir("src")

  inputs.file("index.html")
  inputs.file("package.json")
  inputs.file("tsconfig.app.json")
  inputs.file("tsconfig.json")
  inputs.file("tsconfig.node.json")
  inputs.file("vite.config.ts")

  outputs.dir("dist")

  args = listOf("build")
}

val pnpmLint = tasks.register<PnpmTask>("pnpmLint") {
  inputs.dir("src")
  inputs.file("eslint.config.js")

  args = listOf("lint")
}

val pnpmDev = tasks.register<PnpmTask>("pnpmDev") {
  args = listOf("dev")
}

tasks.register("dev") {
  dependsOn(pnpmDev)
}

val pnpmStorybook = tasks.register<PnpmTask>("pnpmStorybook") {
  args = listOf("storybook")
}

tasks.register("storybook") {
  dependsOn("pnpmStorybook")
}

tasks.register("watch") {
  dependsOn("pnpmBuild", "pnpmLint")
}

tasks.named("assemble") {
  dependsOn(pnpmBuild)
}

tasks.named("check") {
  dependsOn(pnpmLint)
}