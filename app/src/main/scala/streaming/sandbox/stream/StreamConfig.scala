package streaming.sandbox.stream

import streaming.sandbox.readers.BaseReader
import streaming.sandbox.transformers.BaseTransformer
import streaming.sandbox.writers.BaseWriter

case class StreamConfig(reader: BaseReader, transformer: BaseTransformer, writer: BaseWriter, checkpointLocation: String)
