package com.osacky.flank.gradle

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

open class FlankGradleExtension @Inject constructor(objects: ObjectFactory) : FladleConfig {
  @get:Input
  val flankCoordinates: Property<String> = objects.property(String::class.java).convention("com.github.flank:flank")

  @get:Input
  val flankVersion: Property<String> = objects.property(String::class.java).convention("20.08.3")
  // Project id is automatically discovered by default. Use this to override the project id.
  override val projectId: Property<String> = objects.property()
  @get:Input
  override val serviceAccountCredentials: RegularFileProperty = objects.fileProperty()
  override val useOrchestrator: Property<Boolean> = objects.property(Boolean::class.java).convention(false)
  override val autoGoogleLogin: Property<Boolean> = objects.property(Boolean::class.java).convention(false)
  override val devices: ListProperty<Map<String, String>> = (objects.listProperty(Map::class.java) as ListProperty<Map<String, String>>).convention(listOf(mapOf("model" to "NexusLowRes", "version" to "28")))

  // https://cloud.google.com/sdk/gcloud/reference/firebase/test/android/run
  override val testTargets: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override val testShards: Property<Int> = objects.property()
  override val shardTime: Property<Int> = objects.property()
  override val repeatTests: Property<Int> = objects.property()

  // Shard Android tests by time using historical run data. The amount of shards used is set by `testShards`.
  override val smartFlankGcsPath: Property<String> = objects.property()

  override val resultsHistoryName: Property<String> = objects.property()

  override val flakyTestAttempts: Property<Int> = objects.property(Int::class.java).convention(0)

  // Variant to use for configuring output APK.
  @get:Input
  @get:Optional
  val variant: Property<String> = objects.property()

  /**
   * debugApk and instrumentationApk are [Property<String>] and not [RegularFileProperty] because we support wildcard characters.
   */
  @get:Input
  @get:Optional
  val debugApk: Property<String> = objects.property()
  @get:Input
  @get:Optional
  val instrumentationApk: Property<String> = objects.property()

  override val directoriesToPull: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override val filesToDownload: ListProperty<String> = objects.listProperty(String::class.java)

  override val environmentVariables: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).convention(emptyMap())

  override val recordVideo: Property<Boolean> = objects.property(Boolean::class.java).convention(true)

  override val performanceMetrics: Property<Boolean> = objects.property(Boolean::class.java).convention(true)

  override val resultsBucket: Property<String> = objects.property()

  override val keepFilePath: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override val resultsDir: Property<String> = objects.property()

  override val additionalTestApks: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override val runTimeout: Property<String> = objects.property()

  override val ignoreFailedTests: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override val disableSharding: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override val smartFlankDisableUpload: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override val testRunnerClass: Property<String> = objects.property()

  override val localResultsDir: Property<String> = objects.property()

  override val numUniformShards: Property<Int> = objects.property()

  override val clientDetails: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).convention(emptyMap())

  override val testTargetsAlwaysRun: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override val otherFiles: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).convention(emptyMap())

  override val networkProfile: Property<String> = objects.property()

  override val roboScript: Property<String> = objects.property()

  override val roboDirectives: ListProperty<List<String>> = (objects.listProperty(List::class.java) as ListProperty<List<String>>).convention(emptyList())

  override val testTimeout: Property<String> = objects.property()

  override val outputStyle: Property<String> = objects.property<String>().convention("single")

  override val legacyJunitResult: Property<Boolean> = objects.property<Boolean>().convention(false)

  override val fullJunitResult: Property<Boolean> = objects.property<Boolean>().convention(false)

  @Internal
  val configs: NamedDomainObjectContainer<FladleConfigImpl> = objects.domainObjectContainer(FladleConfigImpl::class.java) {
    FladleConfigImpl(
      name = it,
      projectId = projectId,
      serviceAccountCredentials = objects.fileProperty().convention(serviceAccountCredentials),
      useOrchestrator = useOrchestrator,
      autoGoogleLogin = autoGoogleLogin,
      devices = devices,
      testTargets = testTargets,
      testShards = testShards,
      shardTime = shardTime,
      repeatTests = repeatTests,
      smartFlankGcsPath = smartFlankGcsPath,
      resultsHistoryName = resultsHistoryName,
      flakyTestAttempts = flakyTestAttempts,
      directoriesToPull = directoriesToPull,
      filesToDownload = filesToDownload,
      environmentVariables = environmentVariables,
      recordVideo = recordVideo,
      performanceMetrics = performanceMetrics,
      resultsBucket = resultsBucket,
      keepFilePath = keepFilePath,
      resultsDir = resultsDir,
      additionalTestApks = objects.listProperty<String>().convention(additionalTestApks),
      runTimeout = objects.property<String>().convention(runTimeout),
      ignoreFailedTests = objects.property<Boolean>().convention(ignoreFailedTests),
      disableSharding = disableSharding,
      smartFlankDisableUpload = smartFlankDisableUpload,
      testRunnerClass = testRunnerClass,
      localResultsDir = objects.property<String>().convention(localResultsDir),
      numUniformShards = numUniformShards,
      clientDetails = clientDetails,
      testTargetsAlwaysRun = testTargetsAlwaysRun,
      otherFiles = otherFiles,
      networkProfile = networkProfile,
      roboScript = roboScript,
      roboDirectives = roboDirectives,
      testTimeout = testTimeout,
      outputStyle = objects.property<String>().convention(outputStyle),
      legacyJunitResult = objects.property<Boolean>().convention(legacyJunitResult),
      fullJunitResult = objects.property<Boolean>().convention(fullJunitResult)
    )
  }

  fun configs(closure: Closure<*>) {
    configs.configure(closure)
  }
}
