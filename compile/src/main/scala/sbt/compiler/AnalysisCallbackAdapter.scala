package sbt.compiler

import xsbti.{Severity, Position, AnalysisCallback}
import xsbti.compile.ExtendedCompileProgress
import java.io.File
import xsbti.api.SourceAPI

class AnalysisCallbackAdapter(progress: ExtendedCompileProgress, delegate: AnalysisCallback) extends xsbti.AnalysisCallback{

  def problem(what: String, pos: Position, msg: String, severity: Severity, reported: Boolean) =
    delegate.problem(what, pos, msg, severity, reported)

  def api(sourceFile: File, source: SourceAPI) =
    delegate.api(sourceFile, source)

  def generatedClass(source: File, module: File, name: String) = {
    // NON BOILERPLATE
    progress.generated(source, module, name)

    delegate.generatedClass(source, module, name)
  }

  def binaryDependency(binary: File, name: String, source: File, publicInherited: Boolean) =
    delegate.binaryDependency(binary, name, source, publicInherited)

  def sourceDependency(dependsOn: File, source: File, publicInherited: Boolean) =
    delegate.sourceDependency(dependsOn, source, publicInherited)

  override def usedName(sourceFile: File, names: String) =
    delegate.usedName(sourceFile, names)

  override def nameHashing() = delegate.nameHashing()

  override def beginSource(source: File) = delegate.beginSource(source)

  override def endSource(sourcePath: File) = delegate.endSource(sourcePath)
}
