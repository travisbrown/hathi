package edu.umd.mith.hathi

import java.io.File
import scalaz._, Scalaz._

/** Represents a set of metadata files and a data set.
  */
class Collection(val metadataBase: File, datasetBase: File) extends Dataset(datasetBase)
  with MetadataJson {
  def volume(htid: Htid): Throwable \/ Volume = for {
    metadataFile <- isFile(new File(metadataBase, s"${ htid.toFileName }.json"))
    metadataJson <- contents(metadataFile)
    metadata <- parseVolumeMetadata(htid)(metadataJson)
    pages <- pages(htid)
  } yield Volume(metadata, pages)
}
