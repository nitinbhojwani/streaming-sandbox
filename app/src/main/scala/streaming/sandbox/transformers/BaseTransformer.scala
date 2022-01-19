package streaming.sandbox.transformers

import org.apache.spark.sql.DataFrame

trait BaseTransformer {
  def transform(dataframe: DataFrame): DataFrame
}
