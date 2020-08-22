package bootstrap

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

// TODO: nishi SDK v2 でS3のラッパー関数を作成する
trait S3Wrapper {
  protected val bucketName: String
  protected val region: Region
  private val s3Client = S3Client
    .builder()
    .region(region)
    .build();
}
