package client.extractors

import domain.Block

/**
 * Extractor used to obtain the data related to a block
 */
class BkToTxExtractor extends Extractor[Block, BkToTxExtraction] {
  override def extract(objects: List[Block]): List[BkToTxExtraction] = {
    objects.map(obj => {
      BkToTxExtraction(obj.txids)
    })
  }
}