/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ethiscan.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ethiscan.ReportActivity;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.example.ethiscan.GraphicOverlay;
import com.example.ethiscan.VisionProcessorBase;

import java.util.List;

/** Barcode Detector Demo. */
public class BarcodeScannerProcessor extends VisionProcessorBase<List<Barcode>> {

  private static final String TAG = "BarcodeProcessor";

  private final BarcodeScanner barcodeScanner;

  private Barcode barcodeVal;

  public BarcodeScannerProcessor(Context context) {
    super(context);
    // Note that if you know which format of barcode your app is dealing with, detection will be
    // faster to specify the supported barcode formats one by one, e.g.
    // new BarcodeScannerOptions.Builder()
    //     .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
    //     .build();
    barcodeScanner = BarcodeScanning.getClient();
  }

  public Barcode getBarCode() {
    return barcodeVal;
  }
  @Override
  public void stop() {
    super.stop();
    barcodeScanner.close();
  }

  @Override
  protected Task<List<Barcode>> detectInImage(InputImage image) {
    return barcodeScanner.process(image);
  }

  @Override
  protected void onSuccess(
      @NonNull List<Barcode> barcodes, @NonNull GraphicOverlay graphicOverlay) {
    if (barcodes.isEmpty()) {
      Log.v(MANUAL_TESTING_LOG, "No barcode has been detected");
    }
    for (int i = 0; i < barcodes.size(); ++i) {
      Barcode barcode = barcodes.get(i);
      barcodeVal = barcode;
      graphicOverlay.add(new BarcodeGraphic(graphicOverlay, barcode));
    }
  }

  @Override
  protected void onFailure(@NonNull Exception e) {
    Log.e(TAG, "Barcode detection failed " + e);
  }
}
