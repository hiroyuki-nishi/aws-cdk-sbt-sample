## Requirements
・scala: 2.13.3

・sbt: 1.3.13

・aws-cdk: 1.57.0

・localStack: 0.10.0

・awscli-local: 0.7

・node: 12.18.3

## Documantation
AWS CDK API Reference
https://docs.aws.amazon.com/cdk/api/latest/docs/aws-construct-library.html

Developer Guide
https://docs.aws.amazon.com/cdk/latest/guide/home.html

Fargate CDK Sample Code
https://github.com/aws-samples/aws-cdk-examples/blob/master/java/ecs/fargate-load-balanced-service/src/main/java/com/amazonaws/cdk/examples/ECSFargateLoadBalancedStack.java

-------------------------

SDK for Java version2
https://docs.aws.amazon.com/ja_jp/sdk-for-java/v2/developer-guide/sqs-examples.html

1 diff 2の違い
https://docs.aws.amazon.com/ja_jp/sdk-for-java/v2/migration-guide/whats-different.html

SDK Sample Code
https://github.com/awsdocs/aws-doc-sdk-examples/tree/master/java/example_code

------------------------
1. GraaIVMのインストール手順
https://www.graalvm.org/docs/getting-started-with-graalvm/macos/

2. native-image変換

// 1. Dockerfileからイメージの作成と起動
docker build -t graal-builder-image .
docker run --name graal-builder -dt graal-builder-image

// 2. jarをnative-imageにする
docker cp ./modules/adapter/presentation/graaivm/target/scala-2.12/graaIVM.jar graal-builder:/hello.jar
docker exec -it graal-builder native-image -jar hello.jar -H:EnableURLProtocols=http,https --allow-incomplete-classpath --no-server --no-fallback

docker cp graal-builder:/hello ./hello/bootstrap && zip -Dj hello.zip ./hello/bootstrap


