package axle.ml

import axle.square
import math.sqrt

object FeatureNormalizer {

  import axle.matrix.JblasMatrixFactory._ // TODO: generalize
  type M[T] = JblasMatrix[T]

  trait FeatureNormalizer {

    def normalizedData(): M[Double]

    def normalize(featureList: Seq[Double]): M[Double]

    def denormalize(featureRow: M[Double]): Seq[Double]

    def random(): M[Double]
  }

  class IdentityFeatureNormalizer(X: M[Double]) extends FeatureNormalizer {

    def normalizedData(): M[Double] = X

    def normalize(featureList: Seq[Double]): M[Double] =
      matrix(1, featureList.length, featureList.toArray)

    def denormalize(featureRow: M[Double]): Seq[Double] =
      featureRow.toList

    def random(): M[Double] = matrix(1, X.columns, (0 until X.columns).map(i => math.random).toArray)
  }

  class LinearFeatureNormalizer(X: M[Double]) extends FeatureNormalizer {

    val colMins = X.columnMins
    val colRanges = X.columnMaxs - colMins
    val nd = X.subRowVector(colMins).divRowVector(colRanges)

    def normalizedData(): M[Double] = nd

    def normalize(features: Seq[Double]): M[Double] =
      matrix(1, features.length, features.toArray).subRowVector(colMins).divPointwise(colRanges)

    def denormalize(featureRow: M[Double]): Seq[Double] =
      (featureRow.mulPointwise(colRanges) + colMins).toList

    def random(): M[Double] = matrix(1, X.columns, (0 until X.columns).map(i => math.random).toArray).mulPointwise(colRanges) + colMins
  }

  class ZScoreFeatureNormalizer(X: M[Double]) extends FeatureNormalizer {

    lazy val μs = X.columnMeans
    lazy val σ2s = std(X)
    val nd = zscore(X)

    def normalizedData(): M[Double] = nd

    def normalize(features: Seq[Double]): M[Double] =
      (matrix(1, features.length, features.toArray) - μs).divPointwise(σ2s)

    def denormalize(featureRow: M[Double]): Seq[Double] =
      (σ2s.mulPointwise(featureRow) + μs).toList

    def random(): M[Double] = matrix(1, X.columns, (0 until X.columns).map(i => util.Random.nextGaussian).toArray)
  }

  class PCAFeatureNormalizer(X: M[Double], cutoff: Double) extends FeatureNormalizer {

    lazy val μs = X.columnMeans
    lazy val σ2s = std(X)
    val zd = zscore(X)
    val (u, s) = pca(zd)
    val k = numComponentsForCutoff(s, cutoff)
    val Uk = u(0 until u.rows, 0 until k)

    def normalizedData(): M[Double] = zd ⨯ Uk

    def normalize(features: Seq[Double]): M[Double] =
      (matrix(1, features.length, features.toArray) - μs).divPointwise(σ2s) ⨯ Uk

    def denormalize(featureRow: M[Double]): Seq[Double] =
      ((featureRow ⨯ Uk.t).divPointwise(σ2s) + μs).toList

    def random(): M[Double] = matrix(1, X.columns, (0 until X.columns).map(i => util.Random.nextGaussian).toArray) ⨯ Uk

    // (truncatedSigmas.mulPointwise(featureRow) + truncatedMeans).toList
    // val truncatedSigmas = σ2s ⨯ Uk
    // val truncatedMeans = μs ⨯ Uk
    // ns = (fs - μs) .* σ2s ⨯ Uk
    // (ns ⨯ Uk') ./ σ2s + μs  = fs 
  }

}