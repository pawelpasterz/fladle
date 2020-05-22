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
  override var projectId: Property<String> = objects.property()
  override val serviceAccountCredentials: RegularFileProperty = objects.fileProperty()
  override var useOrchestrator: Property<Boolean> = objects.property(Boolean::class.java).convention(false)
  override var autoGoogleLogin: Property<Boolean> = objects.property(Boolean::class.java).convention(false)
  override var devices: ListProperty<Map<String, String>> = (objects.listProperty(Map::class.java) as ListProperty<Map<String, String>>).convention(listOf(mapOf("model" to "NexusLowRes", "version" to "28")))

  // https://cloud.google.com/sdk/gcloud/reference/firebase/test/android/run
  override var testTargets: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override var testShards: Property<Int> = objects.property()
  override var shardTime: Property<Int> = objects.property()
  override var repeatTests: Property<Int> = objects.property()

  // Shard Android tests by time using historical run data. The amount of shards used is set by `testShards`.
  override var smartFlankGcsPath: Property<String> = objects.property()

  override var resultsHistoryName: Property<String> = objects.property()

  override var flakyTestAttempts: Property<Int> = objects.property(Int::class.java).convention(0)

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

  override var directoriesToPull: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override var filesToDownload: ListProperty<String> = objects.listProperty(String::class.java)

  override var environmentVariables: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).convention(emptyMap())

  override var recordVideo: Property<Boolean> = objects.property(Boolean::class.java).convention(true)

  override var performanceMetrics: Property<Boolean> = objects.property(Boolean::class.java).convention(true)

  override var resultsBucket: Property<String> = objects.property()

  override var keepFilePath: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override var resultsDir: Property<String> = objects.property()

  override var additionalTestApks: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override val runTimeout: Property<String> = objects.property()

  override var ignoreFailedTests: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override var disableSharding: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override var smartFlankDisableUpload: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override var testRunnerClass: Property<String> = objects.property()

  override val localResultsDir: Property<String> = objects.property()

  override var numUniformShards: Property<Int> = objects.property()

  override var clientDetails: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).convention(emptyMap())

  override var testTargetsAlwaysRun: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

  override var otherFiles: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).convention(emptyMap())

  override var networkProfile: Property<String> = objects.property()

  override var roboScript: Property<String> = objects.property()

  override var roboDirectives: ListProperty<List<String>> = (objects.listProperty(List::class.java) as ListProperty<List<String>>).convention(emptyList())

  override var testTimeout: String = "15m"

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
