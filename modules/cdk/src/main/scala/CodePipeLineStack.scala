import java.util

import software.amazon.awscdk.core.{Construct, Stack, StackProps}
import software.amazon.awscdk.services.codebuild.{
  BuildEnvironment,
  BuildSpec,
  LinuxBuildImage,
  PipelineProject
}
import software.amazon.awscdk.services.codecommit.Repository
import software.amazon.awscdk.services.codepipeline.actions.{
  CodeBuildAction,
  CodeCommitSourceAction,
  ManualApprovalAction
}
import software.amazon.awscdk.services.codepipeline.{Artifact, Pipeline, StageProps}
import software.amazon.awscdk.services.iam.Role

class CodePipeLineStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {

  private val env = this.getNode.tryGetContext("env").asInstanceOf[String]

  // Action
  private val sourceArtifact = Artifact.artifact("MyApp")

  private val codeCommitSourceAction = CodeCommitSourceAction.Builder
    .create()
    .actionName("Source")
    .repository(
      Repository.fromRepositoryName(this, "repo", "sign-client") // 任意のrepositoryを指定してください
    )
    .branch("master")
    .output(sourceArtifact)
    .role(Role.fromRoleArn(this, "", "")) // id, 任意のarnを指定してください
    .build()

  private val approvalAction = ManualApprovalAction.Builder
    .create()
    .actionName("Confirm")
    .runOrder(1)
    .build()

  // CodeBuild
  private val codeBuildProject = PipelineProject.Builder
    .create(this, s"sign-$env")
    .buildSpec(BuildSpec.fromSourceFilename("buildspec.yml"))
    .environment(
      BuildEnvironment
        .builder()
        .buildImage(LinuxBuildImage.fromDockerRegistry("pigumergroup/docker-sbt:debian-plus-n"))
        .build()
    )
    .build()

  private val codeBuildAction = CodeBuildAction.Builder
    .create()
    .actionName("Build")
    .project(codeBuildProject)
    .input(sourceArtifact)
    .outputs(util.Arrays.asList(Artifact.artifact("MyAppBuild")))
    .runOrder(2)
    .build()

  // Stage
  private val sourceStage = StageProps
    .builder()
    .stageName("Source")
    .actions(util.Arrays.asList(codeCommitSourceAction))
    .build()

  private val buildStage = StageProps
    .builder()
    .stageName("Build")
    .actions(util.Arrays.asList(approvalAction, codeBuildAction))
    .build()

  // CodePipeline
  private val pipeline = Pipeline.Builder
    .create(this, s"sign-pipeline-$env")
    .pipelineName(s"sign-pipeline-$env")
    .stages(util.Arrays.asList(sourceStage, buildStage))
    .build()
}
