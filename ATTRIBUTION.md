# Attribution and upstream provenance

## Project identity

**Hermex Android** is an independent Android client project maintained in the
`ComputerByte/hermex-android` repository. It is not the official Hermes WebUI
repository and is not presented as an official product of the upstream project.

- This repository: <https://github.com/ComputerByte/hermex-android>
- Android source origin / related project: <https://github.com/ComputerByte/hermex-android>
- Upstream server and web interface: <https://github.com/nesquena/hermes-webui>
- Hermes Agent: <https://github.com/NousResearch/hermes-agent>

The Android application communicates with a self-hosted Hermes/Hermex server;
it does not replace or repackage the upstream server.

## Upstream-derived material

This project contains API-contract adaptations, documentation adaptations, and
selected artwork derived from `hermes-webui`.

Upstream copyright notice:

```text
Copyright (c) 2025 Hermes Web UI Contributors
```

The upstream material is used under the MIT License. The complete notice is
preserved in [`THIRD_PARTY_NOTICES.md`](THIRD_PARTY_NOTICES.md), and the root
[`LICENSE`](LICENSE) covers this project's own source code.

Branding and trademark permission are separate from copyright licensing. The
record of the branding permission is maintained in
[`docs/BRANDING_PERMISSION.md`](docs/BRANDING_PERMISSION.md).

## Hermex Android changes

Changes made in this repository include the native Android UI, Android
packaging, authentication/session client, offline/local state, provider/model
selection, and compatibility fixes for the current Hermes Agent API. These
changes are maintained under this project's own copyright notice in `LICENSE`.

When redistributing a source copy or a substantial portion of the application,
keep all of the following together:

1. `LICENSE`;
2. this file;
3. `THIRD_PARTY_NOTICES.md`; and
4. the upstream notices included there.

## Release signing

APK signing is a distribution-integrity mechanism, not an attribution or
copyright mechanism. Release APKs must be signed with the project's release or
upload keystore; the private keystore and passwords must never be committed.
See [`SIGNING.md`](SIGNING.md).

For a release APK, publish the SHA-256 digest and the signing certificate
fingerprint with the release notes so users can verify that the file came from
the intended distributor. Do not claim an upstream signature: a Hermex Android
APK is signed by the Hermex Android distributor, not by `hermes-webui` or
`NousResearch`, unless that party explicitly signs and publishes it.

## AI-assisted contribution disclosure

For pull requests or release notes, disclose AI assistance when applicable,
including the provider, exact model, and any notable tools or modes used. This
is separate from copyright attribution and does not transfer ownership of the
contribution by itself.
