package org.ktoggl.jvm

import org.ktoggl.TogglClientBuilder
import org.ktoggl.jvm.JvmTimeUtilProvider

class JvmTogglClientBuilder : TogglClientBuilder(JvmTimeUtilProvider())