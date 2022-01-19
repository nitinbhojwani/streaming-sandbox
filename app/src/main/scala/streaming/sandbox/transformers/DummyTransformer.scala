package streaming.sandbox.transformers

import org.apache.spark.sql.DataFrame

class DummyTransformer extends BaseTransformer {
  def transform(dataframe: DataFrame) =
    dataframe
}
